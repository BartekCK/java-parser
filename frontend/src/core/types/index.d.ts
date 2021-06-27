import React from 'react';

export interface IRoute {
    hash: string;
}

export interface IRefWrapper {
    homeRef: React.RefObject<HTMLDivElement>;
    uploadRef: React.RefObject<HTMLDivElement>;
    editorRef: React.RefObject<HTMLDivElement>;
}
