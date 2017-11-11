file = open('nounlist.txt', 'r')
n=0
words = []
for i in file:
    n += 1
    words.append(i)
    #if n == 20:
     #   break
file.close()

cleaned_words = []
for i in words:
    if '-' not in i and ' ' not in i and len(i) > 6 and len(i) < 13:
        cleaned_words.append(i[:-1])

print(cleaned_words)

file = open('words.txt', 'w')
for i in cleaned_words:
    file.write(i + '\n')
file.close()