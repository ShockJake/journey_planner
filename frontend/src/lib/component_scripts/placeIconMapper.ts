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
    TrainFront,
    LandPlot,
    Palette,
} from '$lib/components/common/Icons.ts';

const icons = new Map<string, typeof Church>([
    ['CHURCH', Church],
    ['CASTLE', Castle],
    ['UNIVERSITY', GraduationCap],
    ['OLD', Landmark],
    ['RESTAURANT', Pizza],
    ['COFFEE', Coffee],
    ['THEATRE', Drama],
    ['STORE', Store],
    ['BUILDING', House],
    ['MAGICK', Sparkles],
    ['MUSEUM', Amphora],
    ['INFO', CircleHelp],
    ['PARK', Shrub],
    ['WATER', Ship],
    ['VIEW', Binoculars],
    ['TRAIN', TrainFront],
    ['DEFAULT', GitCommitVertical],
    ['DISTRICT', LandPlot],
    ['HOUSE', House],
    ['MEMORIAL', Amphora],
    ["SQUARE", LandPlot],
    ["GALLERY", Palette]
]);

export function getIcon(placeType: string): typeof Church {
    if (!icons.has(placeType)) {
        return icons.get('DEFAULT');
    }

    return icons.get(placeType);
}
