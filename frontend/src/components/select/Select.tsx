import React, { ChangeEvent } from 'react';

// types
import { Parser } from '../../core/types/enums';

// styles
import './styles.scss';

type Props = {
    value: Parser
    onChange: (event: ChangeEvent<HTMLSelectElement>) => void;
};

const Select: React.FC<Props> = ({ onChange, value }: Props) => {
    return (
        <div className="custom-select">
            <select onChange={onChange} value={value}>
                <option value={Parser.json}>{Parser.json}</option>
                <option value={Parser.csv}>{Parser.csv}</option>
                <option value={Parser.xml}>{Parser.xml}</option>
                <option value={Parser.yaml}>{Parser.yaml}</option>
            </select>
        </div>
    );
};

export default Select;
