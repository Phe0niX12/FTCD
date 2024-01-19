class Grammar:
    EPSILON = "epsilon"

    def __init__(self, filename):
        self.non_term = []  # non-terminals
        self.term = []  # terminals
        self.start = ""  # starting symbol/ axiom
        self.productions = {}  # finite set of productions
        self.read_from_file(filename)
    def rebuild(self):
        self.non_term = []
        self.term = []
        self.start = ""
        self.productions = {}

    def __processLine(self, line: str, delimiter=' '):
            elements = line.strip().split(delimiter)
            return elements

    def read_from_file(self, file_name: str):
        self.rebuild()
        with open(file_name) as file:
            line = next(file)
            self.non_term = self.__processLine(line)

            line = next(file)
            self.term = self.__processLine(line)

            line = next(file)
            self.start = self.__processLine(line)[0]

            line = file.readline()
            while line.strip() and ' -> ' not in line:
                line = file.readline()

            while line:
                if ' -> ' in line:
                    source, productions = line.split(" -> ")
                    source = source.strip()
                    for production in productions.split('|'):
                        production = production.strip().replace('epsilon', Grammar.EPSILON).split()
                        if source in self.productions:
                            self.productions[source].append(production)
                        else:
                            self.productions[source] = [production]
                line = file.readline()


    def check_cfg(self):
        has_starting_symbol = False
        for key in self.productions.keys():
            if key == self.start:
                has_starting_symbol = True
            if key not in self.non_term:
                return False
        if not has_starting_symbol:
            return False

        for production in self.productions.values():
            for rhs in production:
                for value in rhs:
                    if value not in self.non_term and value not in self.term and value != Grammar.EPSILON:
                        return False
        return True

    def __str__(self):
        result = "N = " + str(self.non_term) + "\n"
        result += "E = " + str(self.term) + "\n"
        result += "S = " + str(self.start) + "\n"
        result += "P = " + str(self.productions) + "\n"
        return result