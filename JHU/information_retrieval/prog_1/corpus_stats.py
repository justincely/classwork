import argparse
from collections import Counter, defaultdict, namedtuple
import logging
logger = logging.getLogger("Corpus Stats")
import numpy as np
import os
import operator
import pprint
import re
import string
import struct
import sys
import textwrap
import time

from bs4 import BeautifulSoup
from nltk.corpus import stopwords

#-- https://pypi.python.org/pypi/snowballstemmer
import snowballstemmer

#-- Named tuples used to increase readability by accessing
#-- by name instead of numerical index.
Term = namedtuple("term", ["frequency", "offset"])
Posting = namedtuple("Posting", ["doc_id", "count"])

#-- Use common stop words from the Natural-language toolkit english corpus
STOP_WORDS = set(stopwords.words('english'))

#-------------------------------------------------------------------------------

def convert_to_document(directory, ext='txt'):
    """ Simple wrapper to convert local files into corpa for analysis

    Not used in codebase

    """

    count = 1
    with open('code_corpus.txt', 'w') as outfile:
        for root,dirs,files in os.walk(directory):
            for filename in files:
                if filename.endswith(ext):
                    print("found {}".format(os.path.join(root, filename)))
                    outfile.write('<P ID={}>\n'.format(count))
                    for line in open(os.path.join(root, filename)).readlines():
                        outfile.write(line+"\n")
                    outfile.write('</P>\n\n')
                    count += 1

#-------------------------------------------------------------------------------

def length(vector):
    """ Calculate vector length """
    return np.sqrt(np.sum(np.array(vector)**2))

#-------------------------------------------------------------------------------

def idf(df, N):
    """ Calculate inverse document frequency

    Parameters:
    -----------
    df : float, array-like
        Document frequency
    N : float,int
        Number of documents in corpus

    Returns:
    --------
    idf : np.ndarray
        Inversy document frequency of term[s]
    """

    df = np.array(df)
    return np.log2(float(N) / df)

#-------------------------------------------------------------------------------

def apply_weight(tf, df, N):
    """ Calculate tfxidf term weight scheme

    Parameters:
    -----------
    tf : float, int, np.ndarray
        Ter-frequency
    df : float, int, np.ndarray
        Document frequency
    N : int, float
        Number of documents in corpus

    Returns:
    --------
    tfxidf : np.ndarray
        TFxIDF weighted vector or single-value
    """

    tf = np.array(tf)
    df = np.array(df)
    return tf * idf(df, N)

#-------------------------------------------------------------------------------

def simple_stem(word):
    """ Perform really simple stemming rules from lectures.

    This is problematic.  E.g. his -> hi

    Parameters:
    -----------
    word : string
        term to stem

    Return:
    -------
    word : string
        Term after stemming rules applied
    """

    if len(word) < 3:
        return word

    if word.endswith("ies") and not (word.endswith('eies') or word.endswith('aies')):
        return word.replace('ies', 'y')
    elif word.endswith('es') and not (word.endswith('aes') or word.endswith('ees') or word.endswith('oes')):
        return word.replace('es', 'e')
    elif word.endswith('s') and not (word.endswith('us') or word.endswith('ss')):
        return word.replace("s", "")

    return word

#-------------------------------------------------------------------------------

def five_stemmer(word):
    """ Stem words longer than 5 characters.

    Parameters:
    -----------
    word : str
        The term to be stemmed.

    word : str
        The first 5 (or fewer) characters of the input term.
    """

    return word[:5]

#-------------------------------------------------------------------------------

def compute_doc_lengths(posting_list, n_docs):
    """ Create all document lengths

    Parameters:
    -----------
    posting_list : dict
        The pre-computed posting list
    n_docs: int
        total number of documents

    Returns:
    --------
    doc_lengths : dict
        doc_id to doc_length mappings

    """
    logger.info("Computing document lengths")
    doc_lengths = {}
    for term, postings in posting_list.items():
        doc_freq = len(postings)
        for post in postings:
            if not post.doc_id in doc_lengths:
                doc_lengths[post.doc_id] = 0

            doc_lengths[post.doc_id] += (post.count * idf(doc_freq, n_docs))**2

    logger.info("normalizing")
    for key, val in doc_lengths.items():
        doc_lengths[key] = np.sqrt(val)

    return doc_lengths

#-------------------------------------------------------------------------------

def rank_documents(query, posting_list, doc_lengths, limit=50, method='cosine'):
    """ Rank available documents for similarity to input query

    Parameters:
    -----------
    query: list
        strings to use as query
    posting_list: dict
        Pre-computed posting list of the available documents
    doc_lengths: dict
        length of each doc_id
    limit: int, optional
        Number of top matches to return
    method: str, optional
        Comparision method to use: cosine similarity or dot product

    Returns:
    --------
    ranks : list
        (doc_id, score) for the top matching results.
    """

    logger.info("Ranking documents for {}".format(sorted(query)))
    if not method in ['cosine', 'dot']:
        raise ValueError("{} argument for method not available.".format(method))

    query_bag = Counter(query)

    query_vec = [query_bag[item] for item in sorted(query_bag.keys())]
    n_docs = len(set([term.doc_id for post in posting_list.values() for term in post]))

    doc_freq = []
    for key in sorted(query_bag.keys()):
        doc_freq.append(len(posting_list[key]))

    if method == 'cosine':
        query_vec = apply_weight(query_vec, doc_freq, n_docs)
        query_length = length(query_vec)

    doc_scores = dict()
    for i, term in enumerate(sorted(query_bag.keys())):
        for posting in posting_list[term]:
            if not posting.doc_id in doc_scores:
                doc_scores[posting.doc_id] = 0

            if method == 'cosine':
                weighted = apply_weight(posting.count, doc_freq[i], n_docs)
                doc_scores[posting.doc_id] += (query_vec[i] * weighted) / (query_length * doc_lengths[posting.doc_id])
            else:
                doc_scores[posting.doc_id] += query_vec[i] * posting.count

    ranked_ids = sorted(doc_scores, key=lambda item: doc_scores[item])[::-1]

    return [(doc_id, doc_scores[doc_id]) for doc_id in ranked_ids[:limit]]

#-------------------------------------------------------------------------------

def clean(text, stemmer='snowball'):
    """Normalize, split, and clean text

    Parameters:
    -----------
    text : str
        Block of text to clean and prepare.
    stemmer : str, opt
        Stemmer to use: [snowball, five, simple]

    Returns:
    --------
    text : str
        Cleaned and prepared text block.
    """

    if not stemmer in ['snowball', 'five', 'simple', 'none']:
        raise ValueError("Stemmer choice not available.")

    text = re.sub("[{}]".format(string.punctuation), " ", text.lower())
    text = text.split()

    if stemmer == 'five':
        text = [five_stemmer(item) for item in text]
    elif stemmer == 'snowball':
        stemmer = snowballstemmer.stemmer('english');
        text = stemmer.stemWords(text)
    elif stemmer == 'simple':
        text = [simple_stem(item) for item in text]
    else:
        pass

    text = [item for item in text if not item in STOP_WORDS]

    return text

#-------------------------------------------------------------------------------

def create_posting_list(filename, stemmer='snowball'):
    """ Build the posting list

    Parameters:
    -----------
    filename: str
        Full path to the input dataset to parse.

    Returns:
    --------
    postings_list: dict
        Post list of term, doc_id, count.

    """
    postings_list = dict()

    body = BeautifulSoup(open(fname), "html.parser")

    logger.info("Parsing the documents")
    for i, tag in enumerate(body.find_all('p')):
        try:
            tag.attrs['id']
        except KeyError:
            print("HTML formatting found, ignoring.")
            continue

        text = clean(tag.text, stemmer=stemmer)
        vocabulary = Counter(text)

        for word, count in vocabulary.items():
            if not word in postings_list:
                postings_list[word] = []

            postings_list[word].append(Posting(int(tag.attrs['id']), count))


    return postings_list

#-------------------------------------------------------------------------------

def write_posting_list(posting_list, outname='posting_list.dat'):
    """ Write out the posting list to a file.

    Output binary file is in bigendian byteorder using 4-byte unsigned integers.
    Output file will be clobbered if it already exists.

    Parameters:
    -----------
    posting_list: dict
        Post list of term, doc_id, count.
    outname: str, opt
        Name of the output file.

    Returns:
    --------
    corpus_dict: dict
        The built dictionary for the corpus.
    """

    corpus_dict = dict()

    last_freq = 0
    with open(outname, 'wb') as outfile:
        for word in sorted(posting_list):

            word_doc_freq = len(posting_list[word])

            #-- 4 bytes, 2x items for the full offset.
            corpus_dict[word] = Term(word_doc_freq, last_freq * 4 * 2)
            last_freq += word_doc_freq

            for post in sorted(posting_list[word], key=lambda item: item.doc_id):
                outfile.write(post.doc_id.to_bytes(4, byteorder='big'))
                outfile.write(post.count.to_bytes(4, byteorder='big'))

    return corpus_dict

#-------------------------------------------------------------------------------

def parse_posting_list(fname, dictionary):
    """ Read the binary inverted file from disk

    Binary file is parsed as bigendian using 4-byte unsigned integers.

    Parameters:
    -----------
    fname: str
        Full path to the inverted file on disk.
    dictionary: dict
        The dictionary of terms, frequencies, offsets for the corpus.

    Returns:
    --------
    postings_list: dict
        Post list of term, doc_id, count.
    """

    logger.info("Parsing corpus posting list")

    postings_list = dict()
    with open(fname, 'rb') as infile:
        for word, values in sorted(dictionary.items(), key=lambda item: item[1].offset):
            postings_list[word] = []

            infile.seek(values.offset)
            for i in range(values.frequency):
                doc_id = struct.unpack('>I', infile.read(4))[0]
                doc_freq = struct.unpack('>I', infile.read(4))[0]
                postings_list[word].append(Posting(doc_id, doc_freq))

    return postings_list

#-------------------------------------------------------------------------------

def write_dict(dictionary, outname="corpus_dict.txt"):
    """ Write dictionary out to a plaintext file

    Output file will be clobbered if it already exists.

    Parameters:
    -----------
    dictionary: dict
        The dictionary of terms, frequencies, offsets for the corpus.
    outname: str, opt
        Name for the output file.
    """

    with open(outname, "w") as outfile:
        for word in sorted(dictionary):
            tup = dictionary[word]
            outfile.write("{} {} {}\n".format(word, tup.frequency, tup.offset))

#-------------------------------------------------------------------------------

def parse_dict(fname):
    """ Read the corpus dictionary from a plaintext file on disk

    Parameters:
    -----------
    fname: str
        Full path to the file on disk.

    Returns:
    --------
    corpus_dict: dict
        The dictionary of terms, frequencies, offsets for the corpus.
    """

    logger.info("Parsing corpus dictionary")

    corpus_dict = dict()
    with open(fname, 'r') as infile:
        for line in infile.readlines():
            word, frequency, offset = line.split()

            frequency = int(frequency)
            offset = int(offset)

            corpus_dict[word] = Term(frequency, offset)

    return corpus_dict

#-------------------------------------------------------------------------------

def setup_logging(log_file="indexer.log"):
    """ Configure logging module to handle the log stream

    Logging will be output

    Parameters:
    -----------
    log_file : str, opt
       filename to output log messages to

    """

    # create the logging file handler
    logging.basicConfig(filename=log_file,
                        format='%(asctime)s - %(name)s - %(funcName)s - %(levelname)s - %(message)s',
                        level=logging.DEBUG)

    #-- handler for STDOUT
    ch = logging.StreamHandler(sys.stdout)
    ch.setLevel(logging.INFO)
    formatter = logging.Formatter('%(asctime)s - %(name)s - %(funcName)s - %(levelname)s - %(message)s')
    ch.setFormatter(formatter)
    logging.getLogger().addHandler(ch)

#-------------------------------------------------------------------------------

def parse_args():
    ''' Parse command line arguments.

    Returns:
    --------
    args: argparse object
    parser: the parser itself
    '''

    description = """Description: Corpus Stats for Information Retrieval.
    """

    parser = argparse.ArgumentParser(formatter_class=argparse.RawDescriptionHelpFormatter,
                                     description=textwrap.dedent(description))

    parser.add_argument('--analyze', '-a', dest='analyze', action="store_true",
                        help='Create inverted file and dictionary.')

    parser.add_argument('--files', '-f', dest='files', nargs='*', type=str,
                        help='Files to parse')

    parser.add_argument('--lookup', '-l', dest='lookup', type=str, default='',
                        help='query or file of queries to lookup in the inverted file.')

    parser.add_argument('--stemmer', '-s', dest='stemmer', type=str, default='snowball',
                        help='Stemmer algorithm to use. [snowball, simple, five, none]')

    parser.add_argument('--method', '-m', dest='method', type=str, default='cosine',
                        help='Comparison algorithm to use.  Defaults to cosine')

    parser.add_argument('--posting', '-p', dest='posting', action="store_true",
                        help='Print full posting during lookup.')

    parser.add_argument('extra', nargs=argparse.REMAINDER,
                        help='extra arguments supplied.  Will stop the command from running.')

    args = parser.parse_args()

    args = parser.parse_args()
    return args, parser

#-------------------------------------------------------------------------------

if __name__ == "__main__":
    """Parse arguments from the command-line and run selected options."""

    setup_logging()
    pp = pprint.PrettyPrinter(indent=4)

    opts, parser = parse_args()
    if opts.extra:
        sys.exit(parser.print_help())

    #-- perform analysis of input file[s].  Create  and
    #-- write out posting list and dictionary as well as print summary stats.
    ### TODO work on multiple files together as a collection?
    ### TODO add command-line options for the output files
    if opts.analyze:
        for fname in opts.files:
            start = time.time()
            logger.info("Processing input file {}".format(fname))

            posting_list = create_posting_list(fname, opts.stemmer)

            n_docs = len(set([term.doc_id for post in posting_list.values() for term in post]))
            n_terms = sum([term.count for post in posting_list.values() for term in post])
            vocab_size = len(posting_list.keys())

            print("{} Documents processed.".format(n_docs))
            print("{} Total terms processed.".format(n_terms))
            print("{} Unique terms found.".format(vocab_size))

            corpus_dict = write_posting_list(posting_list)
            write_dict(corpus_dict)

            print("Inverted file size is {} bytes".format(os.path.getsize('posting_list.dat')))
            print("Dictionary file size is {} bytes".format(os.path.getsize('corpus_dict.txt')))
            print("-- Completed analysis in {} seconds --".format(time.time() - start))

    #-- Perform lookup of a term from the inverted file and Dictionary.  Will
    #-- read in previously created files as lookup.
    ### TODO make the user able to specify filenames for the 2 files.
    if opts.lookup:
        start = time.time()

        all_queries = {}
        if os.path.exists(opts.lookup):
            body = BeautifulSoup(open(opts.lookup), "html.parser")
            for i, tag in enumerate(body.find_all('q')):
                try:
                    tag.attrs['id']
                except KeyError:
                    print("HTML formatting found, ignoring.")
                    continue

                all_queries[int(tag.attrs['id'])] = clean(tag.text, stemmer=opts.stemmer)
        else:
            all_queries['1'] = clean(opts.lookup, stemmer=opts.stemmer)

        bow = Counter(all_queries[sorted(all_queries)[0]])
        logger.info("First query BOW: ")
        pp.pprint(bow)

        in_dict = parse_dict('corpus_dict.txt')
        posting_list = parse_posting_list('posting_list.dat', in_dict)
        n_docs = len(set([term.doc_id for post in posting_list.values() for term in post]))
        doc_lengths = compute_doc_lengths(posting_list, n_docs)

        with open("rankings.txt", 'w') as ofile:
            for qid in sorted(all_queries):
                query = all_queries[qid]
                doc_scores = rank_documents(query, posting_list, doc_lengths, method=opts.method)

                #print("1 Q0 doc_id i score")
                for i, (doc_id, score) in enumerate(doc_scores):
                    ofile.write("{} Q0 {} {} {} ely\n".format(qid, doc_id, i+1, score))
                    #print("{} Q0 {} {} {} ely".format(qid, doc_id, i+1, score))

        print("-- Completed lookup in {} seconds --".format(time.time() - start))
