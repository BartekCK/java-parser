import React from 'react';
import { useParams } from 'react-router-dom';

// types
import { IRoute } from '../../core/types';

const IndexPage: React.FC = () => {
    const params: IRoute = useParams();

    React.useEffect(() => {
        console.log(params);
    }, [params]);

    return <div>indexpage Ala ma kota</div>;
};

export default IndexPage;
