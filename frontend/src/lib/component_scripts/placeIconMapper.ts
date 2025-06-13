import {
	Church,
	Castle,
	GraduationCap,
	Landmark,
	Pizza,
	Coffee,
	Drama,
	GitCommitVertical,
	Store,
	Sparkles,
	House,
	Amphora,
	CircleHelp,
	Shrub,
	Ship,
	Binoculars,
	TrainFront
} from '$lib/components/common/Icons.ts';

const icons = new Map<string, typeof Church>([
	['CHURCH', Church],
	['CASTLE', Castle],
	['UNIVERSITY', GraduationCap],
	['OLD', Landmark],
	['RESTAURANT', Pizza],
	['COFFEE', Coffee],
	['THEATER', Drama],
	['STORE', Store],
	['BUILDING', House],
	['MAGICK', Sparkles],
	['MUSEUM', Amphora],
	['INFO', CircleHelp],
	['PARK', Shrub],
	['WATER', Ship],
	['VIEW', Binoculars],
	['TRAIN', TrainFront],
	['DEFAULT', GitCommitVertical]
]);

export function getIcon(placeType: string): typeof Church {
	if (!icons.has(placeType)) {
		return icons.get('DEFAULT');
	}

	return icons.get(placeType);
}
