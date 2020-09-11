import matplotlib.pyplot as plt
import sys

arg1 = int(sys.argv[1])
arg2 = int(sys.argv[2])

fig, ax = plt.subplots(figsize=(10, 10))
x = [1, 2]
y = [arg1, arg2]
ax.scatter(x, y)
ax.set_xlabel('Arg number', fontsize=14)
ax.set_ylabel('Value', fontsize=14)
ax.set_xlim(0, len(x)+1)
ax.set_ylim(0, arg2+1)
plt.title(f"What a lovely graph arg:{arg1} arg2:{arg2}", fontsize="16")

plt.savefig(f'public/images/1_{arg1},2_{arg2}.png')

print("Done")
