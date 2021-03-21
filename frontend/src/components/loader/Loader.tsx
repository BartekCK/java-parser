import React from 'react';

// styles
import './styles.scss';

type Props = {
    color?: string;
};

const Loader: React.FC<Props> = ({ color }: Props) => {
    return <div className="lds-dual-ring"></div>;
};

export default Loader;
