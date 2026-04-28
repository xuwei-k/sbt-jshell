import * as cache from '@actions/cache';

const res = await cache.restoreCache(['foo'], 'scala-steward', ['scala-steward']);
console.log(res);
