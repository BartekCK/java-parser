import React from 'react';
import { BrowserRouter, Route, Switch } from 'react-router-dom';

// components
import IndexPage from './pages/index-page';

const App: React.FC = () => {
    return (
        <BrowserRouter>
            <Switch>
                <Route exact path={['/', '/:hash']}>
                    <IndexPage />
                </Route>
            </Switch>
        </BrowserRouter>
    );
};

export default App;
