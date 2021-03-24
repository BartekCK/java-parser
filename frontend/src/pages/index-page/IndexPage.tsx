import React from 'react';

// components
import Loader from '../../components/loader';
import { ToastContainer } from 'react-toastify';

// custom hooks
import useQuery from '../../hooks/useQuery';

// types
import { IRefWrapper } from '../../core/types';

// lazy components
const Header = React.lazy(() => import('../../containers/header'));
const UploadContainer = React.lazy(() => import('../../containers/upload-container'));
const EditorContainer = React.lazy(() => import('../../containers/editor-container'));
const ActivityContainer = React.lazy(() => import('../../containers/activity-container'));
const GoUp = React.lazy(() => import('../../components/go-up'));

const IndexPage = React.forwardRef((props, wrapperRef: React.RefObject<IRefWrapper>) => {
    const query = useQuery();

    React.useEffect(() => {
        console.log(query.get('hash'));
    }, [query]);

    return (
        <div className="index--page" ref={wrapperRef.current?.homeRef}>
            <React.Suspense fallback={<Loader />}>
                <ToastContainer />
                <Header />
                <UploadContainer ref={wrapperRef.current?.uploadRef} />
                <EditorContainer ref={wrapperRef.current?.editorRef} />
                <ActivityContainer ref={wrapperRef.current?.activityRef} />
                <GoUp ref={wrapperRef.current?.uploadRef} />
            </React.Suspense>
        </div>
    );
});

export default IndexPage;
