from Grammar import Grammar

class LLParser:
    def __init__(self, grammar, seq_file):
        self.grammar = grammar
        self.first_sets = {}
        self.follow_sets = {}
        self.parsing_table = {}
        self.construct_first_sets()
        self.construct_follow_sets()
        self.construct_parsing_table()
        self.sequence = self.read_sequence(seq_file)

    @staticmethod
    def read_sequence(seq_file):
        seq = []
        with open(seq_file) as f:
            line = f.readline()
            while line:
                seq.append(line.strip())
                line = f.readline()
        return seq

    def construct_first_sets(self):
        for nt in self.grammar.non_term:
            self.first_sets[nt] = set()
        for nt in self.grammar.non_term:
            self.calculate_first_set(nt)

    def calculate_first_set(self, nt):
        if nt in self.first_sets[nt]:
            return
        for prod in self.grammar.productions[nt]:
            if prod[0] in self.grammar.term:
                self.first_sets[nt].add(prod[0])
            elif prod[0] in self.grammar.non_term:
                self.calculate_first_set(prod[0])
                self.first_sets[nt] |= self.first_sets[prod[0]]

    def construct_follow_sets(self):
        for nt in self.grammar.non_term:
            self.follow_sets[nt] = set()
        self.follow_sets[self.grammar.start].add('$')
        for nt in self.grammar.non_term:
            self.calculate_follow_set(nt)

    def calculate_follow_set(self, nt):
        for prod in self.grammar.productions.keys():
            for prod_value in self.grammar.productions[prod]:
                if nt in prod_value:
                    idx = prod_value.index(nt)
                    if idx < len(prod_value) - 1:
                        if prod_value[idx + 1] in self.grammar.term:
                            self.follow_sets[nt].add(prod_value[idx + 1])
                        elif prod_value[idx + 1] in self.grammar.non_term:
                            self.follow_sets[nt] |= self.first_sets[prod_value[idx + 1]]
                            if '' in self.first_sets[prod_value[idx + 1]]:
                                self.follow_sets[nt] |= self.follow_sets[prod[idx + 1]]

    def construct_parsing_table(self):
        for nt in self.grammar.non_term:
            for term in self.grammar.term:
                if (nt, term) in self.parsing_table:
                    # Skip if entry already exists
                    continue
                prod = self.find_prod(nt, term,set())
                if prod:
                    self.parsing_table[(nt,term)] = prod
                else:
                    for follow_sym in self.follow_sets[nt]:
                        if(nt, follow_sym) not in self.parsing_table:
                            prod = self.find_prod(nt, follow_sym, set())
                            if prod:
                                self.parsing_table[(nt, follow_sym)]= prod

    def find_prod(self,nt, term, seen_nt):
        seen_nt.add(nt)
        for prod in self.grammar.productions[nt]:
            first_of_prod = self.calculate_first(prod, seen_nt)
            if term in first_of_prod:
                return prod

        return None

    def calculate_first(self, symbols, seen_nt):
        first_set = set()
        for symbol in symbols:
            if symbol in self.grammar.term:
                first_set.add(symbol)
                break
            elif symbol in self.grammar.non_term and symbol not in seen_nt:
                seen_nt.add(symbol)
                first_set.update(self.calculate_first(self.grammar.productions[symbol][0],seen_nt))
                if 'epsilon' not in first_set:
                    break
        return first_set

    def parse(self):
        stack = ['$',self.grammar.start]
        input_str = self.sequence
        input_str += '$'
        input_index = 0
        stack_top = stack[-1]

        while stack_top != '$':

            current_input = input_str[input_index]
            if stack_top in self.grammar.term:
                if stack_top == current_input:
                    stack.pop()
                    input_index += 1
                else:
                    print("Error: Mismatch between stack and input.")
                    return False
            elif stack_top in self.grammar.non_term:
                if (stack_top, current_input) in self.parsing_table:
                    production = self.parsing_table[stack_top, current_input]
                    stack.pop()
                    if production[0] != '':
                        stack.extend(reversed(production))
                else:
                    print("Error: No entry in parsing table.")
                    return False
            else:
                print("Error: Invalid symbol on the stack.")
                return False

            stack_top = stack[-1]
        print("Input accepted.")
        return True


if __name__ == "__main__":
    grammar = Grammar("g1.txt")
    ll_parser = LLParser(grammar, 'seq1.txt')
    ll_parser.parse()