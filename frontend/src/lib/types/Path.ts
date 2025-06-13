import type Point from './Point.ts';

export default interface Path {
	time: number;
	distance: number;
	points: Point[];
}
