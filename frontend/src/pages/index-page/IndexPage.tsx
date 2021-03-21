import React from 'react';

// hooks
import { useParams } from 'react-router-dom';

// components
import Header from '../../containers/header';
import UploadContainer from '../../containers/upload-container';
import EditorContainer from '../../containers/editor-container';
import ActivityContainer from '../../containers/activity-container';

// types
import { IRefWrapper, IRoute } from '../../core/types';

const IndexPage = React.forwardRef((props, wrapperRef: React.RefObject<IRefWrapper>) => {
    const params: IRoute = useParams();

    React.useEffect(() => {
        console.log(params);
    }, [params]);

    return (
        <div ref={wrapperRef.current?.homeRef}>
            <Header />
            <UploadContainer ref={wrapperRef.current?.uploadRef} />
            <EditorContainer ref={wrapperRef.current?.editorRef} />
            <ActivityContainer ref={wrapperRef.current?.activityRef} />
        </div>
    );
});

export default IndexPage;
