import type Path from '$lib/types/Path.ts';

let initialPath: Path = { time: 0, distance: 0, points: [] };
let currentPath = $state<Path>(initialPath);

export const currentPathState = {
	get value() {
		return currentPath;
	},
	set value(newState) {
		currentPath = newState;
	}
};
