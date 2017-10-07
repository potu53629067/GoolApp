// Generated code from Butter Knife. Do not modify!
package com.peng.holder;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class HomeHolder$$ViewBinder<T extends com.peng.holder.HomeHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558480, "field 'mItemAppinfoIvIcon'");
    target.mItemAppinfoIvIcon = finder.castView(view, 2131558480, "field 'mItemAppinfoIvIcon'");
    view = finder.findRequiredView(source, 2131558481, "field 'mItemAppinfoTvTitle'");
    target.mItemAppinfoTvTitle = finder.castView(view, 2131558481, "field 'mItemAppinfoTvTitle'");
    view = finder.findRequiredView(source, 2131558482, "field 'mItemAppinfoRbStars'");
    target.mItemAppinfoRbStars = finder.castView(view, 2131558482, "field 'mItemAppinfoRbStars'");
    view = finder.findRequiredView(source, 2131558483, "field 'mItemAppinfoTvSize'");
    target.mItemAppinfoTvSize = finder.castView(view, 2131558483, "field 'mItemAppinfoTvSize'");
    view = finder.findRequiredView(source, 2131558484, "field 'mItemAppinfoTvDes'");
    target.mItemAppinfoTvDes = finder.castView(view, 2131558484, "field 'mItemAppinfoTvDes'");
  }

  @Override public void unbind(T target) {
    target.mItemAppinfoIvIcon = null;
    target.mItemAppinfoTvTitle = null;
    target.mItemAppinfoRbStars = null;
    target.mItemAppinfoTvSize = null;
    target.mItemAppinfoTvDes = null;
  }
}
