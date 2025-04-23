let testState = $state({ someValue: 0 });

export const testObject = {
    get value() {
        return testState;
    },
    set value(newState) {
        testState = newState;
    }
}

