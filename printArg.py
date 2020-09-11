import sys

arg = sys.argv[1]

file = open("test.txt", "w")

file.write(arg)
file.close()

print("Done")
