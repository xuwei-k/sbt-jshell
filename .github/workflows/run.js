import restoreCache from require(`${process.env.GITHUB_WORKSPACE}/node_modules/@actions/cache`);

console.log(cache);
const res = await restoreCache('foo', 'scala-steward', ['scala-steward']);
console.log(res);
