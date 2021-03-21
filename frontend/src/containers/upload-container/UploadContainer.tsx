import React from 'react';

// components
import UploadPlace from '../../components/upload-place';

// styles
import './styles.scss';

const UploadContainer = React.forwardRef((props, divRef: React.RefObject<HTMLDivElement>) => {
    return (
        <div ref={divRef} className="upload--wrapper">
            <UploadPlace />
        </div>
    );
});

export default UploadContainer;
