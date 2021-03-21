import React from 'react';

// styles
import './styles.scss';

const DownloadPlace = React.forwardRef((props, divRef: React.RefObject<HTMLDivElement>) => {
    return (
        <div ref={divRef} className="download--container">
            ala ma kota
        </div>
    );
});

export default DownloadPlace;
