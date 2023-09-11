# Lab02 Mock-up
# Writing Lab02 in Python first before translating to Java

import random as r
import time as t

# Lists of AA's and their corresponding 1 letter code in identical order
letterCode = ["A", "R", "N", "D", "C", "Q", "E", "G", "H", "I", "L", "K", "M", "F", "P", "S", "T", "W", "Y", "V"]
aminoAcids = ["alanine", "arginine", "asparagine", "aspartic acid", "cysteine", "glutamine", "glutamic acid", "glycine",
              "histidine", "isoleucine", "leucine", "lysine", "methionine", "phenylalanine", "proline", "serine",
              "threonine", "tryptophan", "tyrosine", "valine"]

# Randomly select order
r.shuffle(aminoAcids)
score = 0
start_time = t.time()

# Quiz
while (t.time() - start_time) < 30:
    # Generate a random index between 0 and 19
    random_index = r.randint(0, 19)
    amino_acid = aminoAcids[random_index]
    
    print(f"What is the one-character code for the amino acid: {amino_acid}?")
    user_input = input().strip().upper()  # Ignore case and remove extra spaces

    # Check if the answer is correct
    if user_input == letterCode[random_index]:
        print("Correct!\n")
        score += 1
    else:
        print(f"Wrong! The correct answer is {letterCode[random_index]}\n")
        break  # End the quiz if there's a wrong answer

# Display the final score
print(f"Your score: {score}/{len(aminoAcids)}")
