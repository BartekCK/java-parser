import React, { ChangeEvent } from 'react';

// components
import Select from '../select';

// types
import { Parser } from '../../core/types/enums';

// styles
import './styles.scss';

type Props = {
    selectValue: Parser;
    onChangeType: (event: ChangeEvent<HTMLSelectElement>) => void;
    text: string;
    setText?: React.Dispatch<string>;
    readonly?: boolean;
};

const Editor: React.FC<Props> = (props: Props) => {
    const { onChangeType, readonly, selectValue, text, setText } = props;

    const onChange = (event) => {
        if (setText) {
            setText(event.target.value);
        }
    };

    return (
        <div className="editor--container">
            <div className="header">
                <Select onChange={onChangeType} value={selectValue} />
            </div>
            <textarea value={text} className="editor" readOnly={readonly} onChange={onChange} />
        </div>
    );
};

export default Editor;
