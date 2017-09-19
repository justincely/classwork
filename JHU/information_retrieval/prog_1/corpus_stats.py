from collections import Counter, namedtuple
import os
import struct
import sys
import textwrap

import argparse
from bs4 import BeautifulSoup

#-- Named tuples used to increase readability by accessing
#-- by name instead of numerical index.
Term = namedtuple("term", ["frequency", "offset"])
Posting = namedtuple("Posting", ["doc_id", "count"])

#-------------------------------------------------------------------------------

def clean(text):
    """Normalize, split, and clean text

    Common punctuation terms are removed and replaced with empty strings.
    Everything is forced into lower-case, and everything is split on whitespace.

    Apostrophe's are not replaced, as at this point in analysis, the use of
    contractions may be interesting to users.  Additionally, no stop words are
    removed, as the size of text doesn't yet necessitate their removal.

    Parameters:
    -----------
    text : str
        Block of text to clean and prepare.

    Returns:
    --------
    text : str
        Cleaned and prepared text block.
    """

    for character in ['.', '?', '!', '-']:
        text = text.replace(character, '')

    for character in [',', ':', ';']:
        text = text.replace(character, ' ')

    text = text.lower().split()

    return text

#-------------------------------------------------------------------------------

def create_posting_list(filename):
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

    for i, tag in enumerate(body.find_all('p')):
        text = clean(tag.text)
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
    postings_list = dict()

    with open(fname, 'rb') as infile:
        for word, values in dictionary.items():
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
        for word, tup in dictionary.items():
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

    corpus_dict = dict()
    with open(fname, 'r') as infile:
        for line in infile.readlines():
            word, frequency, offset = line.split()

            frequency = int(frequency)
            offset = int(offset)

            corpus_dict[word] = Term(frequency, offset)

    return corpus_dict

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
                        help='Term to lookup in the inverted file.')

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

    opts, parser = parse_args()
    if opts.extra:
        sys.exit(parser.print_help())

    #-- perform analysis of input file[s].  Create  and
    #-- write out posting list and dictionary as well as print summary stats.
    ### TODO work on multiple files together as a collection?
    ### TODO add command-line options for the output files
    if opts.analyze:
        for fname in opts.files:
            print("Running on {}".format(fname))

            posting_list = create_posting_list(fname)

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

    #-- Perform lookup of a term from the inverted file and Dictionary.  Will
    #-- read in previously created files as lookup.
    ### TODO make the user able to specify filenames for the 2 files.
    if opts.lookup:
        term = clean(opts.lookup)[0]
        in_dict = parse_dict('corpus_dict.txt')
        posting_list = parse_posting_list('posting_list.dat', in_dict)

        print("Looking up {}".format(term))

        if not term in posting_list:
            sys.exit("Term not found.")

        postings = posting_list[term]
        print("Document frequency: {}".format(len(postings)))
        if opts.posting:
            print("Full postings:")
            for item in postings:
                print(item)
