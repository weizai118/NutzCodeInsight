package com.sgaop.idea.codeinsight.navigation;

import com.intellij.codeInsight.daemon.GutterIconNavigationHandler;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.ui.popup.PopupChooserBuilder;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.ui.awt.RelativePoint;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBList;
import com.intellij.util.ui.JBUI;
import com.sgaop.idea.codeinsight.util.NutzLineUtil;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2018/9/3
 */
public abstract class AbstractNavigationHandler implements GutterIconNavigationHandler {
    /**
     * 是否匹配跳转条件
     *
     * @param psiElement
     * @return
     */
    public abstract boolean canNavigate(PsiElement psiElement);

    /**
     * 检索符合的资源文件
     *
     * @param psiElement
     * @return
     */
    public abstract List<VirtualFile> findTemplteFileList(PsiElement psiElement);

    @Override
    public final void navigate(MouseEvent mouseEvent, PsiElement psiElement) {
        if (canNavigate(psiElement)) {
            final Project project = psiElement.getProject();
            final List<VirtualFile> fileList = findTemplteFileList(psiElement);
            if (fileList.size() == 1) {
                FileEditorManager.getInstance(project).openFile(fileList.get(0), true);
            } else if (fileList.size() > 1) {
                final List<VirtualFile> infos = new ArrayList<>(fileList);
                final JBList list = new JBList(infos);
                list.setFixedCellHeight(25);
                PopupChooserBuilder builder = JBPopupFactory.getInstance().createListPopupBuilder(list).setTitle("   请选择要打开的模版文件   ");
                builder.setCancelOnClickOutside(true);
                builder.setCancelOnWindowDeactivation(true);
                list.installCellRenderer(vfile -> {
                    VirtualFile tempVfile = (VirtualFile) vfile;
                    Icon icon = NutzLineUtil.getTemplateIcon(tempVfile.getExtension());
                    final String path = tempVfile.getCanonicalPath()
                            .replace(project.getBasePath(), "")
                            .replace("/src/main/webapp/", "    ")
                            .replace("/src/main/resources/", "    ")
                            .replace("WEB-INF/", "    ") + "   ";
                    final JBLabel nameLable = new JBLabel(path, icon, SwingConstants.LEFT);
                    nameLable.setBorder(JBUI.Borders.empty(2));
                    return nameLable;
                });
                builder.setItemChoosenCallback(() -> {
                    final VirtualFile value = (VirtualFile) list.getSelectedValue();
                    FileEditorManager.getInstance(project).openFile(value, true);
                }).createPopup().show(new RelativePoint(mouseEvent));
            } else {
                if (fileList == null || fileList.size() <= 0) {
                    JOptionPane.showMessageDialog(null, "没有找到这个资源文件，请检查！", "错误提示", JOptionPane.ERROR_MESSAGE, null);
                }
            }
        }

    }
}
