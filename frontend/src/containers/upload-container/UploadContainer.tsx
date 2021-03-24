import React from 'react';

// components
import UploadPlace from '../../components/upload-place';

// styles
import './styles.scss';
import Modal from '../../components/modal';

const UploadContainer = React.forwardRef((props, divRef: React.RefObject<HTMLDivElement>) => {
    return (
        <div ref={divRef} className="upload--wrapper">
            <UploadPlace />
            <Modal isOpen={true}>I Love Node.js</Modal>
        </div>
    );
});

export default UploadContainer;
