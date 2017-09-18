from collections import Counter, namedtuple
import struct
import sys

from bs4 import BeautifulSoup


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

    for character in ['.', ':', ';', '?', '!', '-']:
        text = text.replace(character, '')

    for character in [',']:
        text = text.replace(character, ' ')

    text = text.lower().split()

    return text

def create_posting_list(filename):
    Posting = namedtuple("Posting", ["doc_id", "count"])
    postings_list = dict()

    body = BeautifulSoup(open(fname), "html.parser")

    for i, tag in enumerate(body.find_all('p')[:2]):
        text = clean(tag.text)
        vocabulary = Counter(text)

        for word, count in vocabulary.items():
            if not word in postings_list:
                postings_list[word] = []
            postings_list[word].append(Posting(int(tag.attrs['id']), count))

    return postings_list


def write_posting_list(posting_list, outname='posting_list.dat'):
    corpus_dict = dict()
    Term = namedtuple("term", ["frequency", "offset"])

    last_freq = 0
    with open(outname, 'wb') as outfile:
        for word in sorted(posting_list):

            word_doc_freq = len(posting_list[word])
            corpus_dict[word] = Term(word_doc_freq, last_freq * 4 * 2)
            last_freq += word_doc_freq

            for post in sorted(posting_list[word], key=lambda item: item.doc_id):
                outfile.write(post.doc_id.to_bytes(4, byteorder='big'))
                outfile.write(post.count.to_bytes(4, byteorder='big'))

    for word in sorted(corpus_dict):
        print(word, corpus_dict[word])

    return corpus_dict

def parse_posting_list(fname, dictionary):
    with open(fname, 'rb') as infile:
        for word, values in dictionary.items():
            
            infile.seek(values.offset)
            for i in range(values.frequency):
                doc_id = struct.unpack('>I', infile.read(4))[0]
                doc_freq = struct.unpack('>I', infile.read(4))[0]
                print("{} {} {}".format(word, doc_id, doc_freq))

def analyze_file(filename):
    """Perform aggregate analysis for a filename

    Parameters
    ----------
    filename : str
        Full path to file to read and analyze

    Returns
    -------
    doc_count : int
        number of documents (paragraph) analyzed
    collection_freq: dict
        How many times a word occured in the file
    document_freq: dict
        How many times a word occured in a document
    """

    collection_freq = Counter()
    document_freq = Counter()
    Posting = namedtuple("Posting", ["doc_id", "count"])
    postings_list = dict()

    body = BeautifulSoup(open(fname), "html.parser")

    for i, tag in enumerate(body.find_all('p')):
        text = clean(tag.text)
        vocabulary = Counter(text)
        collection_freq.update(vocabulary)
        document_freq.update(Counter(vocabulary.keys()))

        for word, count in vocabulary.items():
            if not word in postings_list:
                postings_list[word] = []
            postings_list[word].append(Posting(tag.attrs['id'], count))

    return i+1, collection_freq, document_freq


if __name__ == "__main__":
    """Run main program here"""
    fname = sys.argv[1]
    print("Running on {}".format(fname))

    posting_list = create_posting_list(fname)
    print(posting_list)
    corpus_dict = write_posting_list(posting_list)
    parse_posting_list('posting_list.dat', corpus_dict)
