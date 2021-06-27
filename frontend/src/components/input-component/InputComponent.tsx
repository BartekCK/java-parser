import React from 'react';

// styles
import './styles.scss'

type Props = {
    value: string;
    onChange: (event) => void;
};

const InputComponent: React.FC<Props> = (props: Props) => {
    const { onChange, value } = props;

    return <input className="input--ui" value={value} type="text" onChange={onChange} />;
};

export default InputComponent;
