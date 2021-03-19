const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const { CleanWebpackPlugin } = require('clean-webpack-plugin');
const CssMinimizerPlugin = require('css-minimizer-webpack-plugin');
const ForkTsCheckerWebpackPlugin = require('fork-ts-checker-webpack-plugin');

module.exports = {
    entry: './src/index.tsx',
    mode: 'production',
    output: {
        path: path.resolve(__dirname, 'build'),
        publicPath: '/',
        filename: 'bundle-[name].[contenthash].js',
        chunkFilename: 'chunk-[name].[contenthash].js',
    },
    module: {
        rules: [
            {
                test: /\.(ts|js)x?$/,
                exclude: /node_modules/,
                use: {
                    loader: 'babel-loader',
                },
            },
        ],
    },
    plugins: [
        new ForkTsCheckerWebpackPlugin({
            async: false,
            eslint: {
                files: './src/**/*',
            },
        }),
        new HtmlWebpackPlugin({
            template: path.resolve(__dirname, 'public', 'index.html'),
            favicon: 'public/favicon.png',
        }),
        new CleanWebpackPlugin(),
    ],
    resolve: {
        extensions: ['.tsx', '.ts', '.js'],
    },
    optimization: {
        splitChunks: {
            chunks: 'all',
            maxSize: 90000,
            cacheGroups: {
                vendors: {
                    test: /node_modules/,
                    chunks: 'all',
                    name: 'vendors',
                    priority: 10,
                    enforce: true,
                },
            },
        },
        minimize: true,
        minimizer: [new CssMinimizerPlugin()],
    },
};
