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


def plot_barh(measurements: list, offset: float, points: str, name: str):
    _, ax = plt.subplots(figsize=(16, 4))
    ax.barh(situations, measurements)
    ax.set_xlabel("Measurements(ms)")

    for i, value in enumerate(measurements):
        ax.text(value + offset, i, f"{str(value)}ms", va="center")
    ax.set_title(f"Average time per operation, for {points} points")
    ax.invert_yaxis()
    ax.spines["right"].set_visible(False)

    plt.tight_layout()
    print(f"Saving plot {name}")
    plt.savefig(name)


for i in range(0, len(all_measurements)):
    plot_barh(all_measurements[i], 0.00001, num_of_points[i], f"median_{i}.png")
