def default_args(foo=[]):
    foo.append('bar')
    return foo

def you_mad_bro():
    print default_args()
    print default_args()
    print default_args()

if __name__ == '__main__':
    you_mad_bro()
