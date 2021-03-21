import React from 'react';

// hooks
import { useParams } from 'react-router-dom';

// components
import Header from '../../containers/header';
import DownloadPlace from '../../components/download-place';

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
            <DownloadPlace ref={wrapperRef.current?.uploadRef} />
            <DownloadPlace ref={wrapperRef.current?.editorRef} />
            <DownloadPlace ref={wrapperRef.current?.activityRef} />
        </div>
    );
});

export default IndexPage;
