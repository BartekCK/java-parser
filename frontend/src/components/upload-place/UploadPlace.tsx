import React from 'react';

// styles
import './styles.scss';

const UploadPlace = React.forwardRef((props, divRef: React.RefObject<HTMLDivElement>) => {
    return (
        <div ref={divRef} className="upload--container">
            ala ma kota
        </div>
    );
});

export default UploadPlace;
