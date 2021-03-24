import React from 'react';
import { BrowserRouter } from 'react-router-dom';

// components
import IndexPage from './pages/index-page';
import Navigation from './containers/navigation';

// types
import { IRefWrapper } from './core/types';
import useLangChange from "./hooks/useLangChange";

const App: React.FC = () => {
    const homeRef = React.useRef<HTMLDivElement>(null);
    const uploadRef = React.useRef<HTMLDivElement>(null);
    const editorRef = React.useRef<HTMLDivElement>(null);
    const activityRef = React.useRef<HTMLDivElement>(null);

    const wrapperRef = React.useRef<IRefWrapper>({ homeRef, uploadRef, editorRef, activityRef });

    useLangChange();

    return (
        <BrowserRouter>
            <Navigation ref={wrapperRef} />
            <IndexPage ref={wrapperRef} />
        </BrowserRouter>
    );
};

export default App;
