from collections import Counter
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

    text = text.lower().split()

    return text


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

    body = BeautifulSoup(open(fname), "html.parser")

    for i, tag in enumerate(body.find_all('p')):
        text = clean(tag.text)
        vocabulary = Counter(text)

        collection_freq.update(vocabulary)
        document_freq.update(Counter(vocabulary.keys()))

    return i+1, collection_freq, document_freq


if __name__ == "__main__":
    """Run main program here"""
    fname = sys.argv[1]
    print("Running on {}".format(fname))

    doc_count, collection_freq, document_freq = analyze_file(fname)

    print("N Documents: {}".format(doc_count))
    print("N Unique Words: {}".format(len(collection_freq.keys())))
    print("N Words: {}".format(sum(collection_freq.values())))
    print()

    print("The 30 Most Common words:")
    print("{:10} {:6} {:6}".format("word", "times", "docs"))
    print("-------------------------")
    for key, val in collection_freq.most_common()[:30]:
        print("{:10} {:6} {:6}".format(key, val, document_freq[key]))
    print()

    print("Nth most common")
    print("---------------")
    print("100th:  {}".format(collection_freq.most_common()[100]))
    print("500th:  {}".format(collection_freq.most_common()[500]))
    print("1000th: {}".format(collection_freq.most_common()[1000]))
    print()

    only_1 = sum([val for val in document_freq.values() if val == 1])
    print("Number of words that occur in exactly 1 document: {}".format(only_1))
