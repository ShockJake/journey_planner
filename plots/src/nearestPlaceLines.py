import matplotlib.pyplot as plt

situations = [
    "List<Place> with PointsBoxed",
    "List<Place> with PointsPrimitive",
    "Place[] PointsPrimitive",
    "Array of primitives",
    "Array of primitives (two loops)",
]
num_of_points = ["1k", "10K", "1m"]

all_measurements = [
    [0.014, 0.008, 0.007, 0.006, 0.005],
    [0.151, 0.084, 0.078, 0.057, 0.045],
    [1.849, 1.48, 0.716, 0.637, 0.452],
]

line1 = [0.014, 0.151, 1.849]
line2 = [0.008, 0.084, 1.48]
line3 = [0.007, 0.078, 0.716]
line4 = [0.006, 0.057, 0.637]
line5 = [0.005, 0.045, 0.452]

# Example x-axis values
x = [1, 2, 3]

# Plot each line with a label
plt.plot(x, line1, label=situations[0])
plt.plot(x, line2, label=situations[1])
plt.plot(x, line3, label=situations[2])
plt.plot(x, line4, label=situations[3])
plt.plot(x, line5, label=situations[4])

# Add labels and title
plt.xlabel("Number of points")
plt.ylabel("Average time per operation (ms)")
plt.title("Growth of average time per operation for different implementations")
plt.xticks(ticks=x, labels=["10k", "100k", "1m"])
# plt.yscale("log")
# Show legend
plt.legend()
plt.grid(True)

plt.show()
