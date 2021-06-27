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
    readonly?: boolean;
};

const Editor: React.FC<Props> = (props: Props) => {
    const { onChangeType, readonly, selectValue } = props;

    return (
        <div className="editor--container">
            <div className="header">
                <Select onChange={onChangeType} value={selectValue} />
            </div>
            <textarea className="editor" readOnly={readonly} />
        </div>
    );
};

export default Editor;
