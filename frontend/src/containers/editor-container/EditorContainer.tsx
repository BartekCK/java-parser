import React from 'react';

// styles
import './styles.scss';

const EditorContainer = React.forwardRef((props, divRef: React.RefObject<HTMLDivElement>) => {
    return (
        <div ref={divRef} className="editor--wrapper">
            ala ma kota
        </div>
    );
});

export default EditorContainer;
