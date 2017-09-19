def unary(val):
    return ''.join(['1' for i in range(val)] + ['0'])

def gamma(val):
    a = int(np.floor(np.log2(val)))
    binary = bin(val-2**(a))[2:]
    while len(binary) < a:
        binary = '0' + binary
    return "{} {}".format(unary(a), binary)

def delta(val):
    a = int(np.floor(np.log2(val)))
    binary = bin(val-2**(a))[2:]
    while len(binary) < a:
        binary = '0' + binary
    return "{} {}".format(gamma(a).replace(" ", ""), binary)

def varBytes(val):
    output = ''
    b = bin(val)[2:][::-1]
    for i in range(0, len(b), 7):
        snippet = b[i:i+7]
        while len(snippet) < 7:
            snippet = snippet + '0'
        if i == 0:
            bit = '1'
        else:
            bit = '0'
        output += snippet + bit + ' '
    return output[::-1]
