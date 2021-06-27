import React from 'react';

// styles
import './styles.scss';
import Editor from '../../components/editor';
import { Parser } from '../../core/types/enums';

const EditorContainer = React.forwardRef((props, divRef: React.RefObject<HTMLDivElement>) => {
    const [convertFrom, setConvertFrom] = React.useState<Parser>(Parser.json);
    const [convertTo, setConvertTo] = React.useState<Parser>(Parser.yaml);

    const isDisabled: boolean = React.useMemo(() => convertFrom === convertTo, [convertFrom, convertTo]);

    const handleChange = (setterFunc) => (event) => {
        setterFunc(Parser[event.target.value]);
    };

    const handleSubmit = () => {
        console.log('SUBMIT');
    };

    return (
        <div ref={divRef} className="editor--wrapper">
            <Editor onChangeType={handleChange(setConvertFrom)} selectValue={convertFrom} />
            <button className="btn btn-primary" onClick={handleSubmit} disabled={isDisabled}>
                <i className="refresh fa fa-refresh" aria-hidden="true" />
            </button>
            <Editor onChangeType={handleChange(setConvertTo)} selectValue={convertTo} readonly />
        </div>
    );
});

export default EditorContainer;
