import type { AxiosError } from "axios";
import { AxiosError as AError } from "axios";

function mapErrorFromCode(errorCode: number | undefined): string {
    if (errorCode === 500) {
        return "Internal Server Error";
    }
    if (errorCode === 400) {
        return "Bad Data Provided";
    }
    if (errorCode === 401) {
        return "Unauthorized";
    }
    if (errorCode === 403) {
        return "Forbiden";
    }
    if (errorCode === 404) {
        return "Data Not Found";
    }
    return "Something wrong happend...";
}

export default function mapError(error: AxiosError | any): string {
    if (!(error instanceof AError)) {
        return "Unknown error";
    }
    if (!error.response) {
        return mapErrorFromCode(error.status);
    }
    if (error.response.data === '') {
        return mapErrorFromCode(error.status);
    }
    return error.response.data.message;
}

