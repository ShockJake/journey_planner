let accountErrorState = $state(false);

export const currentAccountErrorState = {
    get value() {
        return accountErrorState;
    },
    set value(newValue) {
        accountErrorState = newValue;
    }
}
