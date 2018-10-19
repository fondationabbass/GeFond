import { Injectable } from '@angular/core';
import { CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { WizardWorkflowService } from '../../shared/wizard-workflow.service';
import { WizardHelperService } from '../../shared/wizard-helper.service';

@Injectable()
export class CandidatWzGuardService implements CanActivate {
    
    constructor(private router: Router, private workflowService: WizardWorkflowService, private wizardHelperService: WizardHelperService) { }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
        let path: string = route.routeConfig.path;

        return this.verifyWorkFlow(path);
    }
    verifyWorkFlow(path) : boolean {
        let firstPath = this.workflowService.getFirstInvalidStep(path, this.wizardHelperService.candidatWorkflow);
        if (firstPath.length > 0) {
            let url = `/${firstPath}`;
            this.router.navigate([url]);
            return false;
        };
        return true;
    }
}
