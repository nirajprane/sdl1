package com.example.sdl.menu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sdl.R;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.ArrayList;
import java.util.List;

import static com.example.sdl.Flags.uncheckAll;

public class DocExpandableRecyclerAdapter extends ExpandableRecyclerViewAdapter<MyParentViewHolder,MyChildViewHolder> {
    public ArrayList<Menu> menuList;

    public DocExpandableRecyclerAdapter(List<ParentList> groups, ArrayList<Menu> menuList) {
        super(groups);
        this.menuList=menuList;
    }
    @Override
    public MyParentViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.parent_item, parent, false);
        return new MyParentViewHolder(view);
    }

    @Override
    public MyChildViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_item, parent, false);
        return new MyChildViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(final MyChildViewHolder holder, final int flatPosition, ExpandableGroup group, int childIndex) {
        final ChildList childItem = ((ParentList) group).getItems().get(childIndex);

        holder.onBind(childItem.getTitle());

        final String TitleChild=childItem.getTitle();
        final int TitlePrice=childItem.getPrice();

        if (uncheckAll) {
            holder.checkBox.setChecked(false);
            
        }

        holder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                menuList.add(new Menu(TitleChild,TitlePrice));
                holder.checkBox.setChecked(true);
                Toast toast = Toast.makeText(view.getContext(), menuList.size()+" item selected", Toast.LENGTH_SHORT);
                toast.show();

            }


        });



    }

    @Override
    public void onBindGroupViewHolder(MyParentViewHolder holder, int flatPosition, final ExpandableGroup group) {
        holder.setParentTitle(group);

        if(group.getItems()==null)
        {
            holder.listGroup.setOnClickListener(  new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast toast = Toast.makeText(view.getContext(), group.toString(), Toast.LENGTH_SHORT);
                    toast.show();
                }
            });

        }
    }

    public void uncheckAll(){
        uncheckAll=true;
        notifyDataSetChanged();

    }


}
