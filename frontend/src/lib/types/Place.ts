export default interface Place {
    name: string,
    placeType: string,
    position: {
        lat: number,
        lng: number
    }
}
