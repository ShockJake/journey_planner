export default interface RouteGenerationRequest {
    saveToAccount: boolean,
    routeLongevity: string,
    municipality: string,
    places: string[],
    creationDateTime: string
}
