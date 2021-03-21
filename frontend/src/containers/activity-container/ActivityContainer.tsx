import React from 'react';

// styles
import './styles.scss';

const ActivityContainer = React.forwardRef((props, divRef: React.RefObject<HTMLDivElement>) => {
    return (
        <div ref={divRef} className="activity--wrapper">
            ala ma kota
        </div>
    );
});

export default ActivityContainer;
