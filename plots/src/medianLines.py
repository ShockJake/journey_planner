import matplotlib.pyplot as plt

situations = [
    "List of objects with wrappers",
    "List of objects with primitives",
    "Array of objects with primitives",
    "Array of primitives + VectorAPI",
]
num_of_points = ["100", "1000", "1k", "10K", "1m"]

all_measurements = [
    [0.023, 0.022, 0.017, 0.008],
    [0.455, 0.373, 0.309, 0.154],
    [6.964, 5.320, 5.280, 1.645],
    [80.238, 62.310, 61.948, 34.096],
    [787.7, 635.3, 609.9, 338.7],
]

line1 = [0.023, 0.455, 6.964, 80.238, 787.7]
line2 = [0.022, 0.373, 5.320, 62.31, 635.3]
line3 = [0.017, 0.309, 5.28, 61.948, 609.9]
line4 = [0.008, 0.154, 1.645, 34.096, 338.7]

# Example x-axis values
x = [1, 2, 3, 4, 5]

# Plot each line with a label
plt.plot(x, line1, label=situations[0])
plt.plot(x, line2, label=situations[1])
plt.plot(x, line3, label=situations[2])
plt.plot(x, line4, label=situations[3])

# Add labels and title
plt.xlabel("Number of points")
plt.ylabel("Average time per operation (ms)")
plt.title("Growth of average time per operation for different implementations")
plt.xticks(ticks=x, labels=["100", "1000", "10k", "100k", "1m"])
plt.yscale("log")
plt.xscale("log")
# Show legend
plt.legend()
plt.grid(True)

plt.show()
