import { Injectable } from '@angular/core';
import { CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { WizardWorkflowService } from '../../shared/wizard-workflow.service';
import { WizardHelperService } from '../../shared/wizard-helper.service';

@Injectable()
export class CandidatureWzGuardService implements CanActivate {
    constructor(private router: Router, private workflowService: WizardWorkflowService, private wizardHelperService: WizardHelperService) { }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
        const path: string = route.routeConfig.path;

        return this.verifyWorkFlow(path);
    }
    verifyWorkFlow(path): boolean {
        const firstPath = this.workflowService.getFirstInvalidStep(path, this.wizardHelperService.candidatureWorkflow);
        if (firstPath.length > 0) {
            const url = `/${firstPath}`;
            this.router.navigate([url]);
            return false;
        }
        return true;
    }
}
