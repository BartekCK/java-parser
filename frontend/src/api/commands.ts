// api
import { ApiInstance } from './config';
import { Route } from './routes';

// types
import { AllowFileType } from '../core/types/enums';
import { AxiosResponse } from 'axios';

export const uploadFile = async (file: File, target: AllowFileType): Promise<AxiosResponse> => {
    const formData = new FormData();
    formData.append('file', file);
    return await ApiInstance.post(Route.Upload, formData, {
        params: {
            current: file.type,
            target,
        },
        responseType: 'blob',
    });
};
