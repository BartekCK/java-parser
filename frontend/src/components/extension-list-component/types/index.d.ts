import React from 'react';

// types
import { AllowFileType } from '../../../core/types/enums';

export interface IRenderExtension {
    type: AllowFileType;
    component: React.ReactElement;
}
