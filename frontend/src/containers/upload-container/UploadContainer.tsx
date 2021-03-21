import React from 'react';

// styles
import './styles.scss';

const UploadContainer = React.forwardRef((props, divRef: React.RefObject<HTMLDivElement>) => {
    return (
        <div ref={divRef} className="upload--wrapper">
            ala ma kota
        </div>
    );
});

export default UploadContainer;
