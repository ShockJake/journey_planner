import matplotlib.pyplot as plt

situations = [
    "List<Place> with PointsBoxed",
    "List<Place> with PointsPrimitive",
    "Place[] PointsPrimitive",
    "Array of primitives",
    "Array of primitives (two loops)",
]
num_of_points = ["100", "1k", "10k", "100K", "1m"]

all_measurements = [
    [76.335, 70.074, 70.037, 57.467, 48.542],
    [1380.303, 712.8, 695.082, 559.58, 459.942],
    [0.014, 0.008, 0.007, 0.006, 0.005],
    [0.151, 0.084, 0.078, 0.057, 0.045],
    [1.849, 1.48, 0.716, 0.637, 0.452],
]


def plot_barh(measurements: list, offset: float, points: str, name: str, unit: str):
    _, ax = plt.subplots(figsize=(16, 4))
    ax.barh(situations, measurements)
    ax.set_xlabel(f"Measurements({unit})")

    for i, value in enumerate(measurements):
        ax.text(value + offset, i, f"{str(value)}{unit}", va="center")
    ax.set_title(f"Average time per operation, for {points} points")
    ax.invert_yaxis()
    ax.spines["right"].set_visible(False)

    plt.tight_layout()
    print(f"Saving plot {name}")
    plt.savefig(name)


for i in range(0, len(all_measurements)):
    unit = "ns"
    if i > 1:
        unit = "ms"
    plot_barh(all_measurements[i], 0.00001, num_of_points[i], f"nearest_{i}.png", unit)
