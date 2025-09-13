import matplotlib.pyplot as plt

situations = [
    "Before optimizations",
    "After optimizations",
]

line1 = [15, 44, 101]  # [741, 2601, 3477]
line1_1 = [843, 2601, 3626]
line2 = [1, 3, 33]  # [719, 2106, 3321]
line2_1 = [719, 1806, 3321]

# Example x-axis values
x = [1, 2, 3]  # , 4, 5, 6]

# Plot each line with a label
# plt.figure(figsize=(6, 12))
plt.plot(x, line1_1, label=situations[0])
plt.plot(x, line2_1, label=situations[1])

# Add labels and title
plt.xlabel("Number of requests")
plt.ylabel("Time(ms)")
plt.title("Time needed to process all concurrent requests")
# plt.xticks(ticks=x, labels=["1", "10", "100"])  # , "1k", "5k", "10k"])
plt.xticks(ticks=x, labels=["1k", "5k", "10k"])
# plt.yscale("log")
# Show legend
plt.legend()
plt.grid(True)

plt.show()
