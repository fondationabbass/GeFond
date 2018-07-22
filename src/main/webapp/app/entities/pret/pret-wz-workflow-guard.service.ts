import { Injectable } from '@angular/core';
import {
    CanActivate, Router,
    ActivatedRouteSnapshot,
    RouterStateSnapshot,
    CanLoad, Route
} from '@angular/router';

import { PretWzWorkflowService } from './pret-wz-workflow.service';

@Injectable()
export class PretWzWorkflowGuard implements CanActivate {
    constructor(private router: Router, private workflowService: PretWzWorkflowService) { }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
        let path: string = route.routeConfig.path;

        return this.verifyWorkFlow(path);
    }

    verifyWorkFlow(path) : boolean {
        console.log("Entered '" + path + "' path.");

        let firstPath = this.workflowService.getFirstInvalidStep(path);
        if (firstPath.length > 0) {
            console.log("Redirected to '" + firstPath + "' path which it is the first invalid step.");
            let url = `/${firstPath}`;
            this.router.navigate([url]);
            return false;
        };

        return true;
    }
}


